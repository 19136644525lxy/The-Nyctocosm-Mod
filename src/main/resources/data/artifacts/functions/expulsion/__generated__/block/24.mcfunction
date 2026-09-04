#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score a.temp.UUID0 a.value run data get entity @s UUID[0]
execute store result score a.temp.UUID1 a.value run data get entity @s UUID[1]
execute store result score a.temp.UUID2 a.value run data get entity @s UUID[2]
execute store result score a.temp.UUID3 a.value run data get entity @s UUID[3]
execute as @e[tag=m.killMarker,tag=m.kill.dissonance] at @s run function artifacts:expulsion/__generated__/block/25