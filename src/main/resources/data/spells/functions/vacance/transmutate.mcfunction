#built using mc-build (https://github.com/mc-build/mc-build)

summon marker ~ ~ ~ {Tags:["spell","Vacance","bubble","justsummoned"]}
scoreboard players operation @e[tag=Vacance,tag=bubble,tag=justsummoned] m.spell.power = @s m.spell.power
execute store result score TEMP UUID0 run scoreboard players get @s PUUID0
execute store result score TEMP UUID1 run scoreboard players get @s PUUID1
execute store result score TEMP UUID2 run scoreboard players get @s PUUID2
execute store result score TEMP UUID3 run scoreboard players get @s PUUID3
execute as @e[tag=Vacance,tag=bubble,tag=justsummoned] run function spells:vacance/__generated__/block/496
playsound nyctocosm:spells.transmutate.vacance master @a[distance=..50] ~ ~ ~ 10