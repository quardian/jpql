package com.inho.entities;

import lombok.*;

import javax.persistence.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@NamedQueries({
        @NamedQuery(name  = "Member.findByUsername",
                    query = "select m from Member m where m.username = :username")
})
@SequenceGenerator(name="member_id_seq_generator", sequenceName = "member_id_seq",
        initialValue = 1, allocationSize = 1)
public class Member {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_id_seq_generator")
    private Long id;

    private String username;

    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

    public Member(String username, int age) {
        this.username = username;
        this.age = age;
    }

    public void changeTeam(Team team)
    {
        this.setTeam(team);
        team.getMembers().add(this);
    }

}
