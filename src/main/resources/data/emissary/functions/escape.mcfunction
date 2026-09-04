#built using mc-build (https://github.com/mc-build/mc-build)

tp @s @e[tag=emissaryentrance,tag=subspace,limit=1]
execute at @s run particle flame ~ ~1 ~ 1 1 1 0.05 100
execute as @s[gamemode=adventure] run gamemode survival
function emissary:cutscene/end
execute unless entity @a[predicate=emissary:in_domain] run function emissary:remove_entrance