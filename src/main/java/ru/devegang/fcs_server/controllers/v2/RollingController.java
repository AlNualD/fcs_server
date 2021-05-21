package ru.devegang.fcs_server.controllers.v2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.devegang.fcs_server.additional.RollingFormula;
import ru.devegang.fcs_server.additional.RollingResult;
import ru.devegang.fcs_server.services.RollingService;

@RestController
@RequestMapping("api/v2")
public class RollingController {
    @Autowired
    RollingService rollingService;


//    @GetMapping("/roll")
//    public ResponseEntity<RollingResult> sampleRoll(@RequestBody RollingFormula rollingFormula) {
//        return new ResponseEntity<>(rollingService.regularRoll(rollingFormula), HttpStatus.OK);
//    }

    @GetMapping("/roll/str")
    public ResponseEntity<RollingResult> sampleRoll(@RequestParam String formula) {
        RollingResult result = rollingService.regularRoll(formula);
        return result != null ? new ResponseEntity<>(result,HttpStatus.OK) : new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
