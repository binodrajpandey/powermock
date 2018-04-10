package com.example.powermock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.example.powermock.Dependency;
import com.example.powermock.PowerMockitoExample;
import com.example.powermock.UtilityClass;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ UtilityClass.class /*The class with static method to be mocked*/
	,PowerMockitoExample.class})
//@PrepareForTest(fullyQualifiedNames="com.example.powermock.SystemUnderTest")
public class PowerMockitoExampleTest
{

	@Mock
	Dependency dependencyMock;

	@InjectMocks
	PowerMockitoExample systemUnderTest;

	@Test
	public void powerMockito_MockingAStaticMethodCall() {
		when(dependencyMock.retrieveAllStats()).thenReturn(
				Arrays.asList(1, 2, 3));
		PowerMockito.mockStatic(UtilityClass.class);
		when(UtilityClass.staticMethod(anyLong())).thenReturn(150);
		assertEquals(150, systemUnderTest.methodCallingAStaticMethod());
		//To verify a specific method call
		//First : Call PowerMockito.verifyStatic() 
		//Second : Call the method to be verified
		PowerMockito.verifyStatic();
		UtilityClass.staticMethod(1 + 2 + 3);
		// verify exact number of calls
		//PowerMockito.verifyStatic(Mockito.times(1));

	}
	@Test
	public void powerMockito_CallingAPrivateMethod() throws Exception {
		PowerMockitoExample mock = Mockito.spy(systemUnderTest);
		Mockito.doReturn(42l).when(mock).privateMethodUnderTest();
		assertEquals(42, mock.methodCallingPrivateMethod());
	}
}