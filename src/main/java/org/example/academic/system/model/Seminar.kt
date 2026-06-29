package org.example.academic.system.model

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
class Seminar(name: String, weight: Double, value: Double, private val topic: String?) : Assessment(name, weight, value) 