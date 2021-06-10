package me.rpochyly.thehungygames;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

// Třída pro zpřístupnění toho ArrayListu

public class ContestantListClass {

    //public List<Contestant> contestantList = new ArrayList<>();
    public List<Contestant> contestantList = new ArrayList<>();

    // Toto je ten "Getter", jakoby ta funkce co ho zpřístupní. Ale z nějakého důvodu to vypadá že je to read-only, takže jen tato třída může upravovat. Nevím proč a chtělo by to víc otestovat
    public List<Contestant> getContestantList() {
        return contestantList;
    }
    // Workaround abych mohl přidávat do toho ArrayListu
    // Bohužel nevím proč ale nechce to tam uložit žádnou věc
    // Oj teď jak to píšu jsem si uvědomil proč
    // Já sice vytvořím nový objekt ale nepřidal jsem ho do toho samotného ArrayListu
    // Takže toto asi vím jak upravit
    public void addContestantList(String name, Integer lifes, Integer points) {
        new Contestant(name,lifes,points);
    }

}
