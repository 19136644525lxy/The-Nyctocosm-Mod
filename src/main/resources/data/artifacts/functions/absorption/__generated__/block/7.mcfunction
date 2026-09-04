#built using mc-build (https://github.com/mc-build/mc-build)

tag @s add a.absorption.weakened
scoreboard players operation @s a.absorption.weakenMod = @a[tag=a.Owner,limit=1] m.global.power
scoreboard players operation @s a.absorption.weakenMod += @a[tag=a.Owner,limit=1] m.void.power
scoreboard players operation 0 temp = @s a.absorption.weakenMod
scoreboard players operation 0 temp *= #2 const
scoreboard players operation @s a.absorption.weakenMod = 0 temp
scoreboard players operation @s a.absorption.weakenMod += a.absorption.weakPerc a.value
scoreboard players operation @s m.void.weakness.modifier += @s a.absorption.weakenMod