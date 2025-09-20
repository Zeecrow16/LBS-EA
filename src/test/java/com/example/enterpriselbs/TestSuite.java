package com.example.enterpriselbs;


import com.example.enterpriselbs.QueryHandlers.AdminQueryHandlerTest;
import com.example.enterpriselbs.QueryHandlers.ManagerQueryHandlerTest;
import com.example.enterpriselbs.QueryHandlers.StaffQueryHandlerTest;
import com.example.enterpriselbs.aggregates.LeaveRequestAggregateTest;
import com.example.enterpriselbs.aggregates.StaffAggregateTest;
import com.example.enterpriselbs.controller.AdminControllerTest;
import com.example.enterpriselbs.controller.IdentityControllerTest;
import com.example.enterpriselbs.controller.ManagerControllerTest;
import com.example.enterpriselbs.controller.StaffControllerTest;
import com.example.enterpriselbs.mappers.LeaveRequestMapperTest;
import com.example.enterpriselbs.mappers.StaffMapperTest;
import com.example.enterpriselbs.services.AdminServiceTest;
import com.example.enterpriselbs.services.ManagerServiceTest;
import com.example.enterpriselbs.services.StaffServiceTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        StaffAggregateTest.class,
        LeaveRequestAggregateTest.class,
        StaffAggregateTest.class,
        LeaveRequestAggregateTest.class,

        StaffServiceTest.class,
        ManagerServiceTest.class,
        AdminServiceTest.class,

        StaffMapperTest.class,
        LeaveRequestMapperTest.class,

        StaffQueryHandlerTest.class,
        ManagerQueryHandlerTest.class,
        AdminQueryHandlerTest.class,

        IdentityControllerTest.class,
        AdminControllerTest.class,
        StaffControllerTest.class,
        ManagerControllerTest.class

})
class TestSuite {
}
