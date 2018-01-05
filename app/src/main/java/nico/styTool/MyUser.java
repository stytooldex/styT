package nico.styTool;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by luxin on 15-12-8.
 * http://luxin.gitcafe.io
 */
public class MyUser extends BmobUser {
    private Integer score;//用户积分
    private BmobFile auvter;
    private String phone;
    private String id;
    private String Hol;
    private String address;//捐(๑ó﹏ò
    private Integer sex;
    private String personality;
    private List<String> hobby;
    private String num;
    private Integer age;
    private String cardNumber;
    private String bankName;
    private Boolean gender;
    private Boolean gen_;
    private Boolean gen_v;
    private Integer PlayScore_;
    private Integer PlayScore_s;
    /**
     * 游戏得分
     */
    private Integer playScore;

    /**
     * 签到得分
     */
    private Integer signScore;

    /**
     * 游戏（玩家所玩的游戏）
     */
    private String game;

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getGame() {
        return game;
    }

    public void setGame(String game) {
        this.game = game;
    }

    public Integer getPlayScore() {
        return playScore;
    }

    public void setPlayScore(Integer playScore) {
        this.playScore = playScore;
    }

    public Integer getSignScore() {
        return signScore;
    }

    public void setSignScore(Integer signScore) {
        this.signScore = signScore;
    }


    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public List<String> getHobby() {
        return hobby;
    }

    public void setHobby(List<String> hobby) {
        this.hobby = hobby;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonality() {
        return personality;
    }

    public void setPersonality(String personality) {
        this.personality = personality;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public BmobFile getAuvter() {
        return auvter;
    }

    public void setAuvter(BmobFile auvter) {
        this.auvter = auvter;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


    public String getHol() {
        return Hol;
    }

    public void setHol(String Hol) {
        this.Hol = Hol;
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }


    public Boolean getGen_() {
        return gen_;
    }

    public void setGen_(Boolean gen_) {
        this.gen_ = gen_;
    }

    public Boolean getGen_v() {
        return gen_v;
    }

    public void setGen_v(Boolean gen_v) {
        this.gen_v = gen_v;
    }

    public Integer getPlayScore_() {
        return PlayScore_;
    }

    public void setPlayScore_(Integer playScore_) {
        PlayScore_ = playScore_;
    }

    public Integer getPlayScore_s() {
        return PlayScore_s;
    }

    public void setPlayScore_s(Integer playScore_s) {
        PlayScore_s = playScore_s;
    }
}
