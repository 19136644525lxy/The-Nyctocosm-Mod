#built using mc-build (https://github.com/mc-build/mc-build)

execute at @s rotated ~ -90 run function effects:elements/radiance/transmutate/play
tag @s remove justsummoned
scoreboard players operation @s PUUID0 = TEMP UUID0
scoreboard players operation @s PUUID1 = TEMP UUID1
scoreboard players operation @s PUUID2 = TEMP UUID2
scoreboard players operation @s PUUID3 = TEMP UUID3
scoreboard players operation 1 temp = @s m.spell.power
scoreboard players operation 1 temp *= #50 const
scoreboard players operation 0 temp = Vacance s.duration
scoreboard players operation 0 temp += 1 temp
scoreboard players operation @s spelltimer = 0 temp