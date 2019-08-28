package example.micronaut

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import io.micronaut.http.HttpStatus
import io.micronaut.http.MediaType
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post

@CompileStatic
@Controller("/leak")
@Slf4j
class PostFormLeakController {


    @Post(value = "/create-form", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    HttpStatus createUrlEncoded(@Body Object body) {
        log.info "createUrlEncoded, body: '${body}'"
        return HttpStatus.OK
    }

    @Post(value = "/create-form-no-body", consumes = MediaType.APPLICATION_FORM_URLENCODED)
    HttpStatus createUrlEncodedNoBody() {
        log.info "createUrlEncoded, no body"
        return HttpStatus.OK
    }


}
