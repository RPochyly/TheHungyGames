package me.rpochyly.thehungygames;

import java.util.ArrayList;
import java.util.List;

public class ContestantListClass {

  public List<Contestant> contestantList = new ArrayList<>();

  public List<Contestant> getContestantList() {
    return contestantList;
  }

  public void addContestantList(String name, Integer lifes, Integer points) {
    contestantList.add(new Contestant(name, lifes, points));

  }

  public void removeContestantList(Contestant contestant) {
    contestantList.remove(contestant);
  }

  public Contestant getByName(String name) {
    for (int i = 0; i < contestantList.size(); i++) {
      // String contestantName = contestantList.get(i).name;
      if (contestantList.get(i).name.equalsIgnoreCase(name)) {
        return contestantList.get(i);
      }
    }
    return null;
  }
}
