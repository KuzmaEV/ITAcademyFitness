package by.mk_jd2_92_22.userSecurity.services.mappers;

import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.dto.PageDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PageDTOMapper<T> {

    public PageDTO<T> mapper(Page<T> page){

        return new PageDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                (int)page.getTotalElements(),
                page.isFirst(),
                page.getNumberOfElements(),
                page.isLast(),
                page.getContent());
    }

    public PageDTO<T> mapper(Page<UserFull> page, List<T> content){
        return new PageDTO<>(
                page.getNumber(),
                page.getSize(),
                page.getTotalPages(),
                (int)page.getTotalElements(),
                page.isFirst(),
                page.getNumberOfElements(),
                page.isLast(),
                content);
        }
}
