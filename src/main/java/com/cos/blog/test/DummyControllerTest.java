package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

//html파일이 아니라 data를 리턴해주는 RestController
@RestController //응답만한다
public class DummyControllerTest {

    @Autowired //의존성주입(DI)
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try{
            userRepository.deleteById(id);
        }catch(EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }
        return "id : "  + id + "가 삭제되었습니다.";
    }

    @Transactional //함수 종료시에 자동 commit됨
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        //RequestBody : json데이터를 요청하면 자바 오브젝트(MessageConverter의 Jackson라이브러리가 변환해서 받아준다)
        System.out.println("id : " + id);
        System.out.println("password : " + requestUser.getPassword());
        System.out.println("email : " + requestUser.getEmail());

        //object를 들고 올때 영속화된다.
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("수정에 실패하였습니다.");
        });

        //영속화된 데이터를 변경한다.
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        /*save 함수는 id를 전달하지 않으면 insert를 해주고
        save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
        save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.*/
        //userRepository.save(user);
        //@Transactional을 걸면 save 해주지 않아도 실제로 update가 수행된다
        //
        return user;
    } //종료시 영속화된 object 를 commit 한다 (데이터 변경을 자동감지하여 update가 됨) -> 더티체킹



    //전체 select
    //http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list(){

        System.out.println("전체 user 리스트입니다.=====================");
        return userRepository.findAll();

    }

    //한 페이지당 2건의 데이터를 리턴받는다.
    //http://localhost:8000/blog/dummy/user?page=0
    @GetMapping("/dummy/user")
    public Page<User> pageList(@PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser =  userRepository.findAll(pageable);
        List<User> users = pagingUser.getContent();
        return pagingUser; //page리턴
    }

    //건별 select
    //{id} 주소로 파라미터를 전달받을 수 있음.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        //findById 반환타입은 Optional이다.
        //없는 데이터를 찾을 경우 nullexception이 발생할 경우를 대비해서
        //optional 로 user 객체를 감싸서 가져올테니 null 인지 아닌지를 판단해서 return해라
        //람다식
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("id : " + id + "인 해당 유저는 없습니다.");
        });
        /*User user =  userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("id : " + id + "인 해당 유저는 없습니다.");
            }
        });*/

        //요청은 웹브라우저가 하는데
        //user 객체는 자바 오브젝트라 브라우저가 이해하지 못한다
        //따라서 변환을 해줘야 하는데 이게 json 타입 (과거 spring은 Gson라이브러리를 씀)
        //스프링부트는 MessageConverter라는 애가 응답시에 자동 작동한다.
        //자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson라이브러리를 호출해서
        //user 오브젝트를 json으로 변환해서 브라우저에 던져준다.
        return user;
    }

    //http://localhost:8000/blog/dummy/join (요청)
    //http의 body에 username, password, email 데이터를 가지고 요청
    @PostMapping("dummy/join")
    //public String join(String username, String password, String email){ //key=value 형태(약속된 규칙)
        /*System.out.println("username : " + username);
        System.out.println("password : " + password);
        System.out.println("email" + email);*/
    public String join(User user){
        System.out.println("username : " + user.getUsername());
        System.out.println("password : " + user.getPassword());
        System.out.println("email : " + user.getEmail());

        //user.setRole("user");
        user.setRole(RoleType.USER); //role 기본값 USER로 지정

        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
