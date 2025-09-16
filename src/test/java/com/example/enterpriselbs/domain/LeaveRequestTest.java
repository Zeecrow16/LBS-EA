package com.example.enterpriselbs.domain;

//class LeaveRequestTest {
//
//    @Test
//    void approve_happyPath() {
//        Identity id = new Identity("lr-1");
//        Identity staff = new Identity("s-1");
//        LeaveRequest lr = new LeaveRequest(id, staff, LocalDate.now(), LocalDate.now().plusDays(2));
//
//        lr.approve();
//
//        assertEquals(LeaveStatus.APPROVED, lr.status());
//    }
//
//    @Test
//    void reject_fromPending() {
//        LeaveRequest lr = new LeaveRequest(new Identity("lr-2"), new Identity("s-2"), LocalDate.now(), LocalDate.now().plusDays(1));
//
//        lr.reject();
//
//        assertEquals(LeaveStatus.REJECTED, lr.status());
//    }
//
//    @Test
//    void cancel_alreadyCancelled_throws() {
//        LeaveRequest lr = new LeaveRequest(new Identity("lr-3"), new Identity("s-3"), LocalDate.now(), LocalDate.now().plusDays(1));
//
//        lr.cancel();
//
//        IllegalStateException exception = assertThrows(IllegalStateException.class, lr::cancel);
//        assertEquals("Already cancelled", exception.getMessage());
//    }
//
//    @Test
//    void invalidDates_throws() {
//        assertThrows(IllegalArgumentException.class, () ->
//                new LeaveRequest(new Identity("x"), new Identity("s"), LocalDate.now().plusDays(5), LocalDate.now())
//        );
//    }
//}