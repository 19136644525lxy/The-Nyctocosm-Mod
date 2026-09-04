#built using mc-build (https://github.com/mc-build/mc-build)

execute if score SerpentInterp value matches 0 facing entity @e[tag=serpent,tag=towardfacing,limit=1] feet run tp @s ^ ^ ^3
execute if score SerpentInterp value matches 1 facing entity @e[tag=serpent,tag=towardfacing,distance=4.1..,limit=1] feet run tp @s ^ ^ ^4
execute if score SerpentInterp value matches 2 facing entity @e[tag=serpent,tag=target,distance=3.1..,limit=1] feet run tp @s ^ ^ ^3
execute if score SerpentInterp value matches 3 facing entity @e[tag=serpent,tag=towardfacing,distance=6.1..,limit=1] feet run tp @s ^ ^ ^6