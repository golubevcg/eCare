package ecare.services.api;

import ecare.model.dto.UserContractDTO;
import ecare.model.dto.UserDTO;
import ecare.model.entity.User;
import java.util.List;

public interface UserService {
    void save(User user);
    void update(User user);

    void convertToEntityAndUpdate(UserDTO userDTO);

    void delete(User user);
    List<User> getUserByLogin(String login);
    User convertDTOtoEntity(UserDTO userDTO);
    void convertToEntityAndSave(UserDTO userDTO);
    UserDTO getUserDTOByLoginOrNull(String login);
    List<UserDTO> getUserDTOByPassportInfo(String passportInfo);
    List<UserDTO> searchForUserByLogin(String searchInput);
    List<User> getUserByEmail(String email);
    List<UserDTO> getUserDTOByEmail(String email);

    void submitUserOnControllerData(UserContractDTO userForm, String roleCheckbox,
                                    String selectedTariff, String[] selectedOptionsArray);
}
