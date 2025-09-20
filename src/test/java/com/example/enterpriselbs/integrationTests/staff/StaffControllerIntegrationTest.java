package com.example.enterpriselbs.integrationTests.staff;

import com.example.enterpriselbs.application.QueryHandlers.StaffQueryHandler;
import com.example.enterpriselbs.application.commands.CancelLeaveCommand;
import com.example.enterpriselbs.application.controllers.staff.StaffController;
import com.example.enterpriselbs.application.dto.LeaveRequestDto;
import com.example.enterpriselbs.application.services.applicationService.StaffApplicationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@Import({StaffController.class})
public class StaffControllerIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StaffApplicationService staffService;

    @Mock
    private StaffQueryHandler staffQueryHandler;

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.openMocks(this);
    }

    private final String staffId = "S001";

    @BeforeEach
    void setUp() {
        reset(staffService, staffQueryHandler);
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void requestLeaveIntegration() throws Exception {
        mockMvc.perform(post("/api/staff/request-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"staffId\":\"s-1\",\"startDate\":\"2025-09-21\",\"endDate\":\"2025-09-23\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string(notNullValue()));
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void cancelLeaveRequest_Accepted() throws Exception {
        CancelLeaveCommand cmd = new CancelLeaveCommand("lr-1");

        doNothing().when(staffService).cancelLeaveRequest(cmd.getLeaveRequestId());

        mockMvc.perform(delete("/api/staff/cancel-leave")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"leaveRequestId\":\"lr-1\"}"))
                .andExpect(status().isAccepted());

        verify(staffService, times(1)).cancelLeaveRequest("lr-1");
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void viewLeaveStatus_Found() throws Exception {
        LeaveRequestDto dto = new LeaveRequestDto();
        dto.setId("lr-1");

        when(staffQueryHandler.findLeaveRequests(staffId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/api/staff/{staffId}/leave-status", staffId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value("lr-1"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void viewLeaveStatus_NotFound() throws Exception {
        when(staffQueryHandler.findLeaveRequests(staffId)).thenReturn(List.of());

        mockMvc.perform(get("/api/staff/{staffId}/leave-status", staffId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void getRemainingLeave_Found() throws Exception {
        when(staffQueryHandler.findRemainingLeave(staffId)).thenReturn(10);

        mockMvc.perform(get("/api/staff/{staffId}/remaining-leave", staffId))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"STAFF"})
    void getRemainingLeave_NotFound() throws Exception {
        when(staffQueryHandler.findRemainingLeave(staffId)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(get("/api/staff/{staffId}/remaining-leave", staffId))
                .andExpect(status().isNotFound());
    }

}