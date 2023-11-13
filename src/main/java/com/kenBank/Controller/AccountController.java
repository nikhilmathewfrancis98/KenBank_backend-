package com.kenBank.Controller;

import com.kenBank.Repository.AccountsRepository;
import com.kenBank.pojo.Accounts;
import com.kenBank.pojo.Sample;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Node;


@RestController
@Slf4j
public class AccountController {
    @Autowired
    AccountsRepository accountsRepository;
    @GetMapping("/myAccount")
    public Accounts getAccountDetails(@RequestParam int id, Authentication authentication) {

//        String Username=authentication.getName();
//        return "Here are the account details from the DB. Welcome "+Username;
        Accounts accounts = accountsRepository.findByCustomerId(id);
        if (accounts != null ) {
            return accounts;
        }else {
            return null;
        }
    }

}


/**
 * The below code is set inorder to track the no of users just logged in using the basic logic not w.r.t the Spring Sec logic
 *
 *
 * package com.kenBank.Controller;
 *
 * import com.kenBank.pojo.Sample;
 * import lombok.extern.slf4j.Slf4j;
 * import org.springframework.context.annotation.Scope;
 * import org.springframework.security.core.Authentication;
 * import org.springframework.web.bind.annotation.GetMapping;
 * import org.springframework.web.bind.annotation.RestController;
 * import org.w3c.dom.Node;
 *
 * //@Scope()
 * class NodeUser{
 *
 *     Sample user;
 *
 *     @Override
 *     public String toString() {
 *         return "NodeUser{" +
 *                 "CurrentUser=" + user +
 *                 "NextUser"+nextUser+
 *                 '}';
 *     }
 *
 *     public NodeUser(Sample user) {
 *         this.user = user;
 *         this.nextUser=null;
 *     }
 *
 *     NodeUser nextUser;
 * }
 * @RestController
 * @Slf4j
 * public class AccountController {
 *     private String loggedUsers="";
 *     private int flag;
 *     NodeUser head;
 *     NodeUser currNode;
 *     @GetMapping("/myAccount")
 *     public String getAccountDetails(Authentication authentication) {
 *         ++flag;
 *         log.error(String.valueOf(flag));
 *         System.out.println();
 *         String Username=authentication.getName();
 *         NodeUser node=new NodeUser(new Sample(Username,"Nora Kunnumpurath",25));
 *         if (head==null){
 *             head=node;
 *         }else {
 *             currNode=head;
 *             while(currNode.nextUser!=null){
 *                 currNode=currNode.nextUser;
 *             }
 *             currNode.nextUser=node;
 *         }
 *         System.out.println(head);
 *         return "Here are the account details from the DB. Welcome "+Username;
 *     }
 *     @GetMapping("/UserLogStmt")
 *     public void getLoggedInUsersDetails(){
 *         log.error("Inside LogStmt");
 *             if (head!=null){
 *                 NodeUser finalNode=head;
 *                 while(finalNode!=null){
 *                     loggedUsers+=finalNode.user.getName()+" --> ";
 *                     finalNode=finalNode.nextUser;
 *                 }
 *             }
 *
 *         System.out.println(loggedUsers);
 *     }
 * }
 */