package by.mk_jd2_92_22.userSecurity.services.mappers;

import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.model.UserMe;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMeMapper {
    public UserMe mapper(UserFull user){

        return new UserMe(user.getUuid(),
                user.getDtCreate(),
                user.getDtUpdate(),
                user.getMail(),
                user.getNick(),
                user.getRole(),
                user.getStatus());
    }

    public List<UserMe> mapperList(List<UserFull> users){
        ArrayList<UserMe> userMeList = new ArrayList<>();
        for (UserFull user : users) {
            userMeList.add(mapper(user));
        }
        return userMeList;
    }
}
