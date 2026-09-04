#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ -90 run function effects:elements/radiance/transmutate/play
scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #100 const
scoreboard players operation 0 temp += Resistance s.duration
scoreboard players operation @s s.resistance_timer = 0 temp
tag @s add resistant
particle crit ~ ~1 ~ 0.2 0.3 0.2 0.1 100