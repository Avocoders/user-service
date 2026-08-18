package io.github.avocoders.userservice.dtos;

public class UserDto {
    private final Long id;
    private final String name;
    private final String email;

    public UserDto(Long id, String name, String email){
        this.id = id;
        this.name = name;
        this.email = email;
    }
    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getEmail(){
        return email;
    }
    @Override
    public String toString(){
        return "UserDto { id = " + id + ", name = "
                + name + ", email = " + email
                + " }";
    }
}
