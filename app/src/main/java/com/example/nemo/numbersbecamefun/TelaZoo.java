package com.example.nemo.numbersbecamefun;

import java.io.Serializable;

public class TelaZoo implements Serializable {

    private Integer id;
    private String pergunta;
    private Integer img;

    public TelaZoo(Integer id, String pergunta, Integer img) {
        this.id = id;
        this.pergunta = pergunta;
        this.img = img;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public Integer getImg() {
        return img;
    }

    public void setImg(Integer img) {
        this.img = img;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

