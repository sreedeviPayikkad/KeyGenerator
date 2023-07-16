package io.pastebin.keygenerationservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "KeyInfo", indexes = @Index(columnList = "key"))
public class KeyInfo {

    @Id
    @SequenceGenerator(
            name = "key_id_sequence",
            sequenceName = "key_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "key_id_sequence"
    )
    private Long id;


    @Column(name = "key")
    private String key;

    public KeyInfo(String key) {
        this.key = key;
    }
}
