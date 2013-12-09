/**
 *
 *     Copyright (C) norad.fr
 *
 *     Licensed under the Apache License, Version 2.0 (the "License");
 *     you may not use this file except in compliance with the License.
 *     You may obtain a copy of the License at
 *
 *             http://www.apache.org/licenses/LICENSE-2.0
 *
 *     Unless required by applicable law or agreed to in writing, software
 *     distributed under the License is distributed on an "AS IS" BASIS,
 *     WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *     See the License for the specific language governing permissions and
 *     limitations under the License.
 */
package fr.norad.jaxrs.doc.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface HttpStatus {

    int value();

    int OK_200 = 200;
    int CREATED_201 = 201;
    int ACCEPTED_202 = 202;
    int NO_CONTENT_204 = 204;
    int RESET_CONTENT_205 = 205;
    int PARTIAL_CONTENT_206 = 206;
    int MOVED_PERMANENTLY_301 = 301;
    int FOUND_302 = 302;
    int SEE_OTHER_303 = 303;
    int NOT_MODIFIED_304 = 304;
    int USE_PROXY_305 = 305;
    int TEMPORARY_REDIRECT_307 = 307;
    int BAD_REQUEST_400 = 400;
    int UNAUTHORIZED_401 = 401;
    int PAYMENT_REQUIRED_402 = 402;
    int FORBIDDEN_403 = 403;
    int NOT_FOUND_404 = 404;
    int METHOD_NOT_ALLOWED_405 = 405;
    int NOT_ACCEPTABLE_406 = 406;
    int PROXY_AUTHENTICATION_REQUIRED_407 = 407;
    int REQUEST_TIMEOUT_408 = 408;
    int CONFLICT_409 = 409;
    int GONE_410 = 410;
    int LENGTH_REQUIRED_411 = 411;
    int PRECONDITION_FAILED_412 = 412;
    int REQUEST_ENTITY_TOO_LARGE_413 = 413;
    int REQUEST_URI_TOO_LONG_414 = 414;
    int UNSUPPORTED_MEDIA_TYPE_415 = 415;
    int REQUESTED_RANGE_NOT_SATISFIABLE_416 = 416;
    int EXPECTATION_FAILED_417 = 417;
    int INTERNAL_SERVER_ERROR_500 = 500;
    int NOT_IMPLEMENTED_501 = 501;
    int BAD_GATEWAY_502 = 502;
    int SERVICE_UNAVAILABLE_503 = 503;
    int GATEWAY_TIMEOUT_504 = 504;
    int HTTP_VERSION_NOT_SUPPORTED_505 = 505;
}
