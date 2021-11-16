package com.mvc.project.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum State {
    RECEIVED(true),
    PENDENT(false);

    public Boolean type;
}


//DEPOIS VER COMO SERIA COM UM ENUM.... Mas acho q whatever.