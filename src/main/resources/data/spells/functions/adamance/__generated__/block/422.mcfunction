#built using mc-build (https://github.com/mc-build/mc-build)

execute rotated ~ -90 run function effects:elements/void/transmutate/play
scoreboard players operation 0 temp = @s m.spell.power
scoreboard players operation 0 temp *= #40 const
scoreboard players operation 0 temp += Adamance s.duration
scoreboard players operation @s spelltimer = 0 temp
execute at @s run playsound nyctocosm:spells.transmutate.adamance master @a[distance=..50] ~ ~ ~ 10
tag @s remove justsummoned