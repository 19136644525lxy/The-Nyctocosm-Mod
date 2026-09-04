#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ -90 run function effects:elements/radiance/transmutate/play
tag @s add luminous
scoreboard players reset @s luminoustimer
scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #600 const
scoreboard players operation 0 temp += Luminescence s.duration
scoreboard players operation @s luminoustimer = 0 temp