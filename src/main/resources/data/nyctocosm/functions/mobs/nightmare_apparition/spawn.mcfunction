#built using mc-build (https://github.com/mc-build/mc-build)

execute store result score Apparition Count run execute if entity @e[tag=apparition]
execute unless score Apparition Count matches 30.. run function nyctocosm:mobs/nightmare_apparition/summon