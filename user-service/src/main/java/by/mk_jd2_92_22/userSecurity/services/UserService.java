package by.mk_jd2_92_22.userSecurity.services;


import by.mk_jd2_92_22.userSecurity.repositories.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.builder.MyUserBuilder;
import by.mk_jd2_92_22.userSecurity.model.dto.AdminDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.PageDTO;
import by.mk_jd2_92_22.userSecurity.services.api.IUserService;
import by.mk_jd2_92_22.userSecurity.services.mappers.PageDTOMapper;
import by.mk_jd2_92_22.userSecurity.services.mappers.UserMeMapper;
import by.mk_jd2_92_22.userSecurity.services.util.CreatingAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class UserService implements IUserService {

    private final UserFullRepository dao;
    private final UserMeMapper mapperUser;
    private final CreatingAudit creatingAudit;
    private final PageDTOMapper<UserMe> pageDTOMapper;
    private final AccountService accountService;

    public UserService(UserFullRepository dao, UserMeMapper mapperUser,
                       CreatingAudit creatingAudit, PageDTOMapper<UserMe> pageDTOMapper,
                       AccountService accountService) {
        this.dao = dao;
        this.mapperUser = mapperUser;
        this.creatingAudit = creatingAudit;
        this.pageDTOMapper = pageDTOMapper;
        this.accountService = accountService;
    }


    @Override
    @Transactional
    public UserMe create(AdminDTO item, HttpHeaders token) {

        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final UUID uuid = UUID.randomUUID();


        UserFull user = MyUserBuilder.create()
                .setUuid(uuid)
                .setDtCreate(now)
                .setDtUpdate(now)
                .setMail(item.getMail())
                .setNick(item.getNick())
                .setRole(item.getRole())
                .setStatus(item.getStatus())
                .setPassword(item.getPassword())
                .build();

        final UserFull userFull = dao.save(user);

        creatingAudit.create(userFull.getUuid(), "new User created", token);

        return mapperUser.mapper(userFull);
    }

    @Override
    public UserMe get(UUID uuid) {
        final UserFull userFull = dao.findById(uuid).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));
        return mapperUser.mapper(userFull);
    }

    @Override
    public PageDTO<UserMe> get(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserFull> pageUsers = dao.findAll(pageable);

        List<UserMe> content = mapperUser.mapperList(pageUsers.getContent());


        return pageDTOMapper.mapper(pageUsers, content);
    }

    @Override
    @Transactional
    public void update(UUID uuid, LocalDateTime dtUpdate, AdminDTO item, HttpHeaders token) {

        UserFull user = dao.findById(uuid).orElseThrow(() ->
                new UsernameNotFoundException("User not found"));

        final UUID uuidAdmin = accountService.me().getUuid();
        creatingAudit.create(uuidAdmin, "Update user: " + user.getMail(),
                token);

        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);

        if (dtUpdate.isEqual(user.getDtUpdate())){
            user.setDtUpdate(now);
        }else {
            throw new IllegalArgumentException("Не удалось обнавить, было кем-то изменино раньше." +
                    " Попробуйте еще раз!");
        }

        user.setMail(item.getMail());
        user.setNick(item.getNick());
        user.setRole(item.getRole());
        user.setStatus(item.getStatus());
        user.setPassword(item.getPassword());

            dao.save(user);

    }
}
