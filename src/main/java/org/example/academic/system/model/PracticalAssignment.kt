package org.example.academic.system.model

import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter

@Getter
@Setter
@NoArgsConstructor
class PracticalAssignment(name: String, weight: Double, value: Double, private val technology: String?) :
    Assessment(name, weight, value)
