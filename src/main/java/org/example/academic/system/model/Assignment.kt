package org.example.academic.system.model

import lombok.NoArgsConstructor

@NoArgsConstructor
class Assignment(
    name: String,
    weight: Double,
    value: Double
) : Assessment(name, weight, value)
