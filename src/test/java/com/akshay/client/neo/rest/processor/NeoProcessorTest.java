/**
 * 
 */
package com.akshay.client.neo.rest.processor;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.akshay.client.neo.rest.exception.NeoProcessorException;
import com.akshay.client.neo.rest.model.NeoDataCollection;
import com.akshay.client.neo.rest.model.NeoLite;

/**
 * @author AKSHAYH
 *
 */
@RunWith(PowerMockRunner.class)
public class NeoProcessorTest {

	private NeoProcessor neoProcessor = null;
	private NeoDataCollection mockNeoDataCollection = null;
	private ArrayList<NeoLite> mockNeoLiteList = null;

	@Before
	public void setUp() {

		neoProcessor = new NeoProcessor();

		mockNeoDataCollection = mock(NeoDataCollection.class);
		mockNeoLiteList = mock(ArrayList.class);

	}

	@Test
	public void testNeoProcessorWhenNeoDataCollectionIsNull() {

		try {
			neoProcessor.initialize(null);
			fail("Exception must have occured.");
		} catch (NeoProcessorException e) {

			assertTrue(e.getMessage().contains("The NeoDataCollection POJO is null."));
		}

	}

	@Test
	public void testNeoProcessorAttemptToInitializeWithNullInDataCollection() {
		try {	
			neoProcessor = new NeoProcessor(null);
		} catch (NeoProcessorException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("The NeoDataCollection POJO is null."));
		}

	}
	
	@Test
	public void testNeoProcessorFailOnNullNeoListInsideNonNullNeoDataCollection() {
		try {	
			NeoDataCollection neoDataCollection = new NeoDataCollection();
			neoDataCollection.setNear_earth_objects(null);
			neoProcessor = new NeoProcessor(neoDataCollection);
		} catch (NeoProcessorException e) {
			System.out.println(e.getMessage());
			assertTrue(e.getMessage().contains("NEO list in the NeoDataCollection POJO is null."));
		}

	}

}
