#built using mc-build (https://github.com/mc-build/mc-build)

execute if score SerpentAttack value matches 1 at @s run function serpent:attack/dive/subtick
execute if score SerpentAttack value matches 2 at @s run function serpent:attack/laser/subtick
execute if score SerpentAttack value matches 3 at @s run function serpent:attack/beam/subtick