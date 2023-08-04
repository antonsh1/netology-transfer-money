package ru.smartjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.smartjava.objects.ConfirmMessage;
import ru.smartjava.objects.OperationId;
import ru.smartjava.objects.Transfer;
import ru.smartjava.service.TransferService;

import javax.validation.Valid;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @GetMapping("/check")
    public String checker() {
        return "Check Okay";
    }

    @PostMapping("/transfer")
    public OperationId addTransferInfo(@Valid @RequestBody Transfer transfer) {
        return new OperationId(transferService.addTransferInfo(transfer).toString());
    }

    @PostMapping("/confirmOperation")
    public OperationId confirmCode(@Valid @RequestBody ConfirmMessage confirmMessage) {
        transferService.confirmTransfer(confirmMessage);
        return confirmMessage.getOperationId();
    }
}
