package co.edu.uniandes.bicibo.service;

import co.edu.uniandes.bicibo.domain.User;

public class UserService {
/*
    public User getDefaultUser() {
        User user = new User();
        user.setFirstName("Abimelec");
        user.setLastName("Cuesta");
        return user;
    }
  */  
    public User Login() {
        User user = new User();
        user.setId("1");
        user.setNombre("Abimelec");
        user.setStatus("OK");
        user.setMessage("El usuario logueado");
        return user;
    }
    public User Logout() {
        User user = new User();
        user.setStatus("OK");
        user.setMessage("El usuario se ha desautenticdo");
        return user;
    }
    public User Registrar() {
        User user = new User();
        user.setId("2");
        user.setNombre("Abimelec");
        user.setStatus("OK");
        user.setMessage("El usuario registrado");
        return user;
    }
    public User InfoUsuairo(String id) {
        User user = new User();
        user.setId(id);
        
        if(id.equals("1")) {
            user.setNombre("Abimelec");
            user.setemail("acuestap@gmail.com");
            user.setfotoPerfil("http://i393.photobucket.com/albums/pp17/acuestap/U_Andes_Fabricas/Foto_Abimelec_zpspb0uhnlm.jpg");
            user.setedad("35");
        }
        
        return user;
    }
    public User UpdateUsuairo(String id) {
        User user = new User();
        user.setId(id);
        
        if(id.equals("2")) {
            user.setStatus("OK");
            user.setMessage("Usuario Actualizado");
        }
        
        return user;
    }
    public User ListarAmigos(String id) {
        User user = new User();
        user.setStatus("OK");
        user.setMessage("Listado de amigos");
        
        
        return user;
    }
    public User ListarRegistrados(String id) {
        User user = new User();
        user.setStatus("OK");
        user.setMessage("Listado de usuarios que aún no son mis amigos...");
        return user;
    }
    public User AgregarAmigo(String id) {
        User user = new User();
        user.setStatus("OK");
        user.setMessage("Amigo Agregado");
        return user;
    }
    public User ListarRecorridos(String id) {
        User user = new User();
        user.setId(id);
        user.setStatus("OK");
        user.setMessage("Listado de los recorridos de un usuario especifico");
        return user;
    }
    public User AgregarRecorrido(String id) {
        User user = new User();
        user.setId(id);
        user.setStatus("OK");
        user.setMessage("Recorrido individual agregado.");
        return user;
    }
    public User ListarRecorridosGrupo() {
        User user = new User();
        user.setStatus("OK");
        user.setMessage("Listado de los recorridos en grupo disponibles");
        return user;
    }
    public User AgregarRecorridoGrupo(String id) {
        User user = new User();
        user.setId(id);
        user.setStatus("OK");
        user.setMessage("Recorrido grupal agregado.");
        return user;
    }
    public User UnirseRecorridoGrupo(String id) {
        User user = new User();
        user.setId(id);
        user.setStatus("OK");
        user.setMessage("El usuario se acaba de unir al recorrido grupal.");
        return user;
    }
}
