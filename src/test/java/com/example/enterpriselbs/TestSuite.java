package com.example.enterpriselbs;


import com.example.enterpriselbs.aggregates.LeaveRequestAggregateTest;
import com.example.enterpriselbs.aggregates.StaffAggregateTest;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        StaffAggregateTest.class,
        LeaveRequestAggregateTest.class
})
class TestSuite {
}
