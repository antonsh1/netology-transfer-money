package ru.smartjava.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.smartjava.dto.ConfirmMessage;
import ru.smartjava.dto.OperationId;
import ru.smartjava.dto.Transfer;
import ru.smartjava.service.TransferService;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping("/transfer")
    public ResponseEntity<OperationId> addTransferInfo(@Valid @RequestBody Transfer transfer) {
        return ResponseEntity.ok(new OperationId(String.valueOf(transferService.addTransferInfo(transfer))));
    }

    @PostMapping("/confirmOperation")
    public ResponseEntity<OperationId> confirmCode(@Valid @RequestBody ConfirmMessage confirmMessage) {
        transferService.confirmTransfer(confirmMessage);
        return ResponseEntity.ok(confirmMessage.getOperationId());
    }
}
