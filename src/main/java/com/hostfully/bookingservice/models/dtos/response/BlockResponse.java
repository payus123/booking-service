package com.hostfully.bookingservice.models.dtos.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hostfully.bookingservice.models.Block;
import com.hostfully.bookingservice.utils.CommonConstants;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockResponse {
    private String blockId;
    @JsonFormat(pattern = CommonConstants.DATETIME_FORMAT)
    private String startDate;
    @JsonFormat(pattern = CommonConstants.DATETIME_FORMAT)
    private String endDate;
    private String propertyName;
    private String performedAction;


    public static BlockResponse getBlockResponse(Block block, String performedAction){
          return BlockResponse.builder()
                  .blockId(block.getUniqueId())
                  .startDate(block.getStartDate().toString())
                  .endDate(block.getEndDate().toString())
                  .propertyName(block.getPropertyName())
                  .performedAction(performedAction).build();
    }
}
