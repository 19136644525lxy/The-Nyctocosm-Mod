#built using mc-build (https://github.com/mc-build/mc-build)

execute positioned as @e[tag=locator,tag=emissary,tag=center] positioned ~ ~1 ~ run tp @s ~ ~ ~
execute if score @s em.timer matches 15..30 run function emissary:attacks/phase4/godrays/__generated__/block/668