package br.com.cursosEBS.enrollments.http;

import br.com.cursosEBS.enrollments.http.dto.CoursesDTO;
import br.com.cursosEBS.enrollments.http.dto.UsersDTO;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("courses-ms")
public interface CourseClient {

    @RequestMapping(method = RequestMethod.GET, value = "/courses/{id}")
    CoursesDTO getUserById(@PathVariable @NotNull Long id);
}
