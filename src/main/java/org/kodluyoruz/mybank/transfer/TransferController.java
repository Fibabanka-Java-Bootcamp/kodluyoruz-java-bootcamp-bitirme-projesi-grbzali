package org.kodluyoruz.mybank.transfer;


import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/transfers/{accountId}")
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferServiceImp transferServiceImp){
        this.transferService = transferServiceImp;
    }

    @GetMapping(params = {"page", "size"})
    public List<TransferDto> accountStatement(@Min(value = 0) @RequestParam("page") int page, @Min(0) @RequestParam("size") int size,
                                           @PathVariable("accountId") long accountId) {
        return transferService.listAllByAccountId(accountId,PageRequest.of(page, size))
                .stream()
                .map(Transfer::toTransferDto)
                .collect(Collectors.toList());
    }
}
