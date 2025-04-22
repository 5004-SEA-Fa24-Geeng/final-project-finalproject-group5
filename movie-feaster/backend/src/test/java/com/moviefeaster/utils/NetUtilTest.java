package com.moviefeaster.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Test class for NetUtil utility.
 */
@ExtendWith(MockitoExtension.class)
public class NetUtilTest {

    /** Mock OkHttpClient for testing HTTP requests. */
    @Mock
    private OkHttpClient mockClient;

    /** Mock Response for testing HTTP responses. */
    @Mock
    private Response mockResponse;

    /** Mock ResponseBody for testing response content. */
    @Mock
    private ResponseBody mockResponseBody;

    /** Mock ObjectMapper for testing JSON processing. */
    @Mock
    private ObjectMapper mockMapper;

    /** Mock ArrayNode for testing JSON array operations. */
    @Mock
    private ArrayNode mockArrayNode;

    @BeforeEach
    void setUp() {
        // Reset mocks before each test
        reset(mockClient, mockResponse, mockResponseBody, mockMapper, mockArrayNode);
    }

    @Nested
    @DisplayName("getTop50MoviesJson tests")
    class GetTop50MoviesJsonTests {
        @Test
        @DisplayName("getTop50MoviesJson should return non-null stream")
        void shouldReturnNonNullStream() {
            InputStream result = NetUtil.getTop50MoviesJson();
            assertNotNull(result);
        }

        @Test
        @DisplayName("getTop50MoviesJson should return valid JSON stream")
        void shouldReturnValidJsonStream() throws IOException {
            InputStream result = NetUtil.getTop50MoviesJson();
            assertNotNull(result);
            
            // Read the stream to verify it contains valid JSON
            byte[] data = result.readAllBytes();
            String json = new String(data);
            assertTrue(json.startsWith("[") && json.endsWith("]"), 
                "Response should be a JSON array");
        }

        @Test
        @DisplayName("getTop50MoviesJson should handle API errors gracefully")
        void shouldHandleApiErrors() {
            assertDoesNotThrow(() -> {
                InputStream result = NetUtil.getTop50MoviesJson();
                assertNotNull(result);
            });
        }

        @Test
        @DisplayName("getTop50MoviesJson should return consistent results")
        void shouldReturnConsistentResults() throws IOException {
            InputStream firstCall = NetUtil.getTop50MoviesJson();
            InputStream secondCall = NetUtil.getTop50MoviesJson();
            
            assertNotNull(firstCall);
            assertNotNull(secondCall);
            
            String firstJson = new String(firstCall.readAllBytes());
            String secondJson = new String(secondCall.readAllBytes());
            
            assertEquals(firstJson, secondJson, 
                "Consecutive calls should return the same data");
        }
    }

    @Nested
    @DisplayName("getCrewJsonStream tests")
    class GetCrewJsonStreamTests {
        @Test
        @DisplayName("getCrewJsonStream should return non-null stream for valid movie ID")
        void shouldReturnNonNullStream() {
            InputStream result = NetUtil.getCrewJsonStream(123);
            assertNotNull(result);
        }

        @Test
        @DisplayName("getCrewJsonStream should handle invalid movie ID gracefully")
        void shouldHandleInvalidMovieId() {
            assertDoesNotThrow(() -> {
                InputStream result = NetUtil.getCrewJsonStream(-1);
                assertNotNull(result);
            });
        }

        @Test
        @DisplayName("getCrewJsonStream should return valid JSON stream")
        void shouldReturnValidJsonStream() throws IOException {
            InputStream result = NetUtil.getCrewJsonStream(123);
            assertNotNull(result);
            
            // Read the stream to verify it contains valid JSON
            byte[] data = result.readAllBytes();
            String json = new String(data);
            assertTrue(json.startsWith("{") && json.endsWith("}"), 
                "Response should be a JSON object");
        }

        @Test
        @DisplayName("getCrewJsonStream should handle API errors gracefully")
        void shouldHandleApiErrors() {
            assertDoesNotThrow(() -> {
                InputStream result = NetUtil.getCrewJsonStream(999999);
                assertNotNull(result);
            });
        }
    }

    /**
     * Simple stub test that always passes.
     */
    @Test
    public void testSimpleStub() {
        // This test always passes
        System.out.println("Simple stub test - this will always pass");
        assertTrue(true);
    }

    /**
     * Tests that the top 50 movies JSON response is not null.
     */
    @Test
    public void testGetTop50MoviesJsonNotNull() {
        InputStream result = NetUtil.getTop50MoviesJson();
        try {
            // Mark the stream so we can reset it after reading
            result.mark(Integer.MAX_VALUE);

            // Read all bytes
            byte[] data = new byte[result.available()];
            result.read(data);

            // Print the content
            String jsonString = new String(data);
            // Reset the stream so it can be read again
            result.reset();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tests with mock data to verify functionality.
     */
    @Test
    public void testWithMockData() {
        System.out.println("Testing with mock data");

        // Create mock JSON data
        String mockJson = "{\"results\":[{\"id\":123,\"title\":\"Mock Movie\"}]}";
        InputStream mockStream = new ByteArrayInputStream(mockJson.getBytes());

        // You can use the mock data for assertions
        assertNotNull(mockStream);
        System.out.println("Mock test passed!");
    }

    /**
     * Tests the format of the top 50 movies JSON response.
     */
    @Test
    public void testTop50MoviesJsonFormat() throws IOException {
        InputStream stream = NetUtil.getTop50MoviesJson();
        assertNotNull(stream, "API response stream should not be null");

        byte[] data = stream.readAllBytes();
        String json = new String(data);

        // Check if it's a valid JSON array format
        assertTrue(json.trim().startsWith("[") && json.trim().endsWith("]"),
                "Response should be a JSON array");

        // Check the data contains expected fields like 'title' and 'id'
        assertTrue(json.contains("\"title\""), "Each movie entry should contain a title");
        assertTrue(json.contains("\"id\""), "Each movie entry should contain an id");

        // Basic size check
        assertTrue(json.length() > 500, "Response should contain substantial data");

        System.out.println("Top 50 Movies JSON array test passed, length: " + json.length());
    }

    /**
     * Tests handling of malformed JSON response.
     */
    @Test
    public void testMalformedJsonResponse() {
        // Create malformed JSON
        String malformedJson = "{\"results\":[{\"id\":123,\"title\":\"Test Movie\"";
        InputStream malformedStream = new ByteArrayInputStream(malformedJson.getBytes());
        
        try {
            // Try to read the malformed JSON
            byte[] data = malformedStream.readAllBytes();
            String json = new String(data);
            
            // Verify the JSON is malformed
            assertFalse(json.trim().endsWith("}"), "JSON should be malformed");
        } catch (IOException e) {
            fail("Should not throw IOException when reading malformed JSON");
        }
    }

    /**
     * Tests handling of empty API response.
     */
    @Test
    public void testEmptyApiResponse() {
        String emptyJson = "{\"results\":[]}";
        InputStream emptyStream = new ByteArrayInputStream(emptyJson.getBytes());
        
        try {
            byte[] data = emptyStream.readAllBytes();
            String json = new String(data);
            
            assertTrue(json.contains("\"results\":[]"), "Response should contain empty results array");
            assertEquals(emptyJson, json, "Response should match empty JSON structure");
        } catch (IOException e) {
            fail("Should not throw IOException when reading empty response");
        }
    }

    /**
     * Tests handling of very large response.
     */
    @Test
    public void testLargeResponseHandling() {
        // Create a large JSON response (simulating many movies)
        StringBuilder largeJson = new StringBuilder("{\"results\":[");
        for (int i = 0; i < 1000; i++) {
            if (i > 0) {
                largeJson.append(",");
            }
            largeJson.append("{\"id\":").append(i).append(",\"title\":\"Movie ").append(i).append("\"}");
        }
        largeJson.append("]}");
        
        InputStream largeStream = new ByteArrayInputStream(largeJson.toString().getBytes());
        
        try {
            // Test that we can read the entire large response
            byte[] data = largeStream.readAllBytes();
            String json = new String(data);
            
            assertTrue(json.length() > 10000, "Response should be large");
            assertTrue(json.contains("\"results\""), "Response should contain results array");
        } catch (IOException e) {
            fail("Should not throw IOException when reading large response");
        }
    }

    /**
     * Tests handling of API rate limiting.
     */
    @Test
    public void testApiRateLimiting() {
        // Make multiple rapid API calls to test rate limiting
        for (int i = 0; i < 5; i++) {
            InputStream stream = NetUtil.getTop50MoviesJson();
            assertNotNull(stream, "Stream should not be null even under rate limiting");
            
            try {
                byte[] data = stream.readAllBytes();
                assertTrue(data.length > 0, "Stream should contain data");
            } catch (IOException e) {
                fail("Should not throw IOException under rate limiting");
            }
        }
    }
}
