package eCare.services.api;

import eCare.model.dto.UserDTO;
import eCare.model.enitity.User;
import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);
    void delete(User user);
    List<User> getUserByLogin(String login);
    User convertDTOtoEntity(UserDTO userDTO);
    void convertToEntityAndSave(UserDTO userDTO);
    UserDTO getUserDTOByLoginOrNull(String login);
    List<UserDTO> getUserDTOByPassportInfo(Long passportInfo);
    List<UserDTO> searchForUserByLogin(String searchInput);
    List<User> getUserByEmail(String email);
    List<UserDTO> getUserDTOByEmail(String email);

}
