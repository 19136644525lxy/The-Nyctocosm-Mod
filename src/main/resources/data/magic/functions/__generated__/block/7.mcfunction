#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score m.TUUID0 value run data get entity @s attack.player[0]
execute store result score m.TUUID1 value run data get entity @s attack.player[1]
execute store result score m.TUUID2 value run data get entity @s attack.player[2]
execute store result score m.TUUID3 value run data get entity @s attack.player[3]
execute on attacker at @s run function magic:__generated__/block/8
execute if score m.TUUID0 value = m.PUUID0 value if score m.TUUID1 value = m.PUUID1 value if score m.TUUID2 value = m.PUUID2 value if score m.TUUID3 value = m.PUUID3 value run function magic:__generated__/block/9
tag @a remove a.checkMatch
data remove entity @s attack