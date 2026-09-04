#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ -90 if predicate emissary:lightning_field_chance run function effects:lightning/draw_spark
particle wax_on ~ ~3 ~ 0.1 4 0.1 1 5 force
particle electric_spark ~ ~3 ~ 0.1 4 0.1 0.5 5 force