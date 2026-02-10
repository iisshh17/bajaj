import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class BfhlController {

    private final String OFFICIAL_EMAIL = System.getenv("OFFICIAL_EMAIL") != null ? 
            System.getenv("OFFICIAL_EMAIL") : "ishika1249.be23@chitkarauniversity.edu.in";
            
    private final String GEMINI_API_KEY = System.getenv("GEMINI_API_KEY") != null ? 
            System.getenv("GEMINI_API_KEY") : "AIzaSyA1qD80lJ0582TAbR2HvzSl3bGTATmDPnk";

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("is_success", true);
        response.put("official_email", OFFICIAL_EMAIL);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/bfhl")
    public ResponseEntity<Map<String, Object>> processBfhl(@RequestBody Map<String, Object> request) {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("is_success", false); 
        response.put("official_email", OFFICIAL_EMAIL);

        try {
            if (request.size() != 1) return ResponseEntity.status(400).body(response);

            if (request.containsKey("fibonacci")) {
                int n = Integer.parseInt(request.get("fibonacci").toString());
                if (n < 0) throw new IllegalArgumentException();
                response.put("data", getFibonacci(n));
            } 
            else if (request.containsKey("prime")) {
                List<Integer> nums = castToIntegerList(request.get("prime"));
                response.put("data", nums.stream().filter(this::isPrime).collect(Collectors.toList()));
            } 
            else if (request.containsKey("lcm")) {
                List<Integer> nums = castToIntegerList(request.get("lcm"));
                response.put("data", findLCM(nums));
            } 
            else if (request.containsKey("hcf")) {
                List<Integer> nums = castToIntegerList(request.get("hcf"));
                response.put("data", findHCF(nums));
            } 
            else if (request.containsKey("AI")) {
                String question = request.get("AI").toString();
                response.put("data", getAIResponse(question));
            } 
            else {
                return ResponseEntity.status(400).body(response);
            }

            response.put("is_success", true);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(400).body(response);
        }
    }

    private List<Integer> getFibonacci(int n) {
        List<Integer> series = new ArrayList<>();
        int a = 0, b = 1;
        for (int i = 0; i <= n; i++) {
            series.add(a);
            int next = a + b;
            a = b;
            b = next;
        }
        return series;
    }

    private boolean isPrime(int n) {
        if (n <= 1) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private long findGCD(long a, long b) {
        while (b > 0) {
            a %= b;
            long temp = a;
            a = b;
            b = temp;
        }
        return a;
    }

    private long findHCF(List<Integer> nums) {
        if (nums.isEmpty()) return 0;
        long result = nums.get(0);
        for (int i = 1; i < nums.size(); i++) result = findGCD(result, nums.get(i));
        return result;
    }

    private long findLCM(List<Integer> nums) {
        if (nums.isEmpty()) return 0;
        long result = nums.get(0);
        for (int i = 1; i < nums.size(); i++) {
            result = (result * nums.get(i)) / findGCD(result, nums.get(i));
        }
        return result;
    }

    private String getAIResponse(String prompt) {
        try {
            String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=" + GEMINI_API_KEY;
            RestTemplate restTemplate = new RestTemplate();
            String jsonBody = "{ \"contents\": [{ \"parts\":[{\"text\": \"" + prompt + " Answer in strictly one word.\" }] }] }";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

            ResponseEntity<Map> apiResponse = restTemplate.postForEntity(url, entity, Map.class);
            
            List candidates = (List) apiResponse.getBody().get("candidates");
            Map firstCandidate = (Map) candidates.get(0);
            Map content = (Map) firstCandidate.get("content");
            List parts = (List) content.get("parts");
            Map firstPart = (Map) parts.get(0);
            
            return firstPart.get("text").toString().trim().replaceAll("[^a-zA-Z]", ""); 
        } catch (Exception e) {
            return "Error";
        }
    }

    private List<Integer> castToIntegerList(Object obj) {
        return ((List<?>) obj).stream()
                .map(item -> Integer.parseInt(item.toString()))
                .collect(Collectors.toList());
    }
}