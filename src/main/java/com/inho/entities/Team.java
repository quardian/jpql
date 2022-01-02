package com.inho.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
@Entity
@SequenceGenerator(name="team_id_seq_generator", sequenceName = "team_id_seq",
                    initialValue = 1, allocationSize = 1)
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "team_id_seq_generator")
    private Long id;

    @Column(nullable = false, length = 20)
    private String name;

    @OneToMany(mappedBy = "team")
    private final List<Member> members = new ArrayList<>();

    public Team(String name) {
        this.name = name;
    }


}
