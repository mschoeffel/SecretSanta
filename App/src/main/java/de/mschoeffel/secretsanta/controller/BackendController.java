package de.mschoeffel.secretsanta.controller;

import de.mschoeffel.secretsanta.TestClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api")
public class BackendController {

    private static final Logger LOG = LoggerFactory.getLogger(BackendController.class);

    public static final String HELLO_TEXT = "Hello from Spring Boot Backend!";
    public static final String SECURED_TEXT = "Hello from the secured resource!";

    @Autowired
    private TestClientService testClientService;

    @RequestMapping(path = "/hello")
    public String sayHello() {
        LOG.info("GET called on /hello resource");
        return testClientService.findById(123L).getName();
    }

    @RequestMapping(path = "/user/{lastName}/{firstName}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public long addNewUser(@PathVariable("lastName") String lastName, @PathVariable("firstName") String firstName) {
       return 1;
    }

    @GetMapping(path = "/user/{id}")
    public String getUserById(@PathVariable("id") long id) {

        return "EMPTY";
    }

    @RequestMapping(path = "/secured", method = RequestMethod.GET)
    public @ResponseBody
    String getSecured() {
        LOG.info("GET successfully called on /secured resource");
        return SECURED_TEXT;
    }

    // Forwards all routes to FrontEnd except: '/', '/index.html', '/api', '/api/**'
    // Required because of 'mode: history' usage in frontend routing, see README for further details
    @RequestMapping(value = "{_:^(?!index\\.html|api).$}")
    public String redirectApi() {
        LOG.info("URL entered directly into the Browser, so we need to redirect...");
        return "forward:/";
    }

}
