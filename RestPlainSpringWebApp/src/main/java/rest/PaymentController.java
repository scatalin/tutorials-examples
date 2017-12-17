package rest;

import org.springframework.web.bind.annotation.*;

/**
 * The most important class in our API is the controller which acts as the
 * interface for client/server communication, each controller acts as a resource
 * which exposes some services and is accessed via specific url (/payment).
 * <p>
 * #RestController: this annotation marks the class as a Resource, it defines implicitly
 * both #Controller and #ResponseBody mvc annotations, when annotating a class with
 * #RestController, it’s not necessary to write #ResponseBody beside the POJO classes
 * returned from your methods.
 * <p>
 * #RequestMapping: this annotation defines the url of the resource in addition to the method
 * type: GET/POST, in our example we expose the payment service as POST which is accessed
 * through /payment/pay.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    private final String sharedKey = "SHARED_KEY";

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 200;
    private static final int AUTH_FAILURE = 102;

    /**
     * #RequestParam: this annotation represents a specific request parameter, in our example,
     * we map a request parameter called key to an argument key of type String.
     * <p>
     * #RequestBody: this annotation represents the body of the request, in our example, we map
     * the body of the request to a POJO class of type PaymentRequest (jackson handles the JSON/POJO conversion)
     * <p>
     * As noticed the response is represented as BaseResponse and there is no need to annotate it,
     * jackson converts it implicitly to JSON.
     *
     * @param key     the auth key
     * @param request the request
     * @return the response
     */
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public PaymentResponse pay(@RequestParam(value = "key") String key, @RequestBody PaymentRequest request) {

        PaymentResponse response = new PaymentResponse();
        if (sharedKey.equalsIgnoreCase(key)) {
            int userId = request.getUserId();
            String itemId = request.getItemId();
            double discount = request.getDiscount();

            // Process the request
            // ....
            // Return success response to the client.

            response.setStatus(SUCCESS_STATUS);
            response.setCode(CODE_SUCCESS);
        } else {
            response.setStatus(ERROR_STATUS);
            response.setCode(AUTH_FAILURE);
        }
        return response;
    }
}