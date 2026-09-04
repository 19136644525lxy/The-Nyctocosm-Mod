#built using mc-build (https://github.com/mc-build/mc-build)

tag @s remove a.absorption.weakened
scoreboard players operation @s m.void.weakness.modifier -= @s a.absorption.weakenMod
scoreboard players reset @s a.absorption.weakenMod