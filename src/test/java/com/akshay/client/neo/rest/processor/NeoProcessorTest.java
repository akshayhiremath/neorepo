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
	// private NeoLite mockNeoLite = null;
	private ArrayList<NeoLite> mockNeoLiteList = null;

	@Before
	public void setUp() {

		neoProcessor = new NeoProcessor();

		mockNeoDataCollection = mock(NeoDataCollection.class);
		// mockNeoLite = mock(NeoLite.class);
		mockNeoLiteList = mock(ArrayList.class);

	}

	@Test
	public void testNeoProcessorWhenNeoDataCollectionIsNull() {

		try {
			neoProcessor.initialize();
			fail("Exception must have occured.");
		} catch (NeoProcessorException e) {

			assertTrue(e.getMessage().contains("The NeoDataCollection POJO is null."));
		}

	}

	@Test
	public void testNeoProcessorWhenNeoDataIsNullInDataCollection() {

		neoProcessor.setNeoDataCollection(mockNeoDataCollection);
		when(mockNeoDataCollection.getNear_earth_objects()).thenReturn(null);
		try {
			neoProcessor.initialize();
		} catch (NeoProcessorException e) {
			assertTrue(e.getMessage().contains("NEO list in the NeoDataCollection POJO is null."));
		}

	}

}
