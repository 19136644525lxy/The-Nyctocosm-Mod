#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score Walker Count run execute if entity @e[tag=walker,tag=root]
execute unless score Walker Count matches 3.. run function nyctocosm:mobs/death_walker/summon