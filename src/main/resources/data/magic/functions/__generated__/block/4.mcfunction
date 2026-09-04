#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score m.TUUID0 value run data get entity @s interaction.player[0]
execute store result score m.TUUID1 value run data get entity @s interaction.player[1]
execute store result score m.TUUID2 value run data get entity @s interaction.player[2]
execute store result score m.TUUID3 value run data get entity @s interaction.player[3]
execute as @a[tag=m.hypertraceMatched] at @s run function magic:__generated__/block/5
execute if score m.TUUID0 value = m.PUUID0 value if score m.TUUID1 value = m.PUUID1 value if score m.TUUID2 value = m.PUUID2 value if score m.TUUID3 value = m.PUUID3 value run function magic:__generated__/block/6
data remove entity @s interaction